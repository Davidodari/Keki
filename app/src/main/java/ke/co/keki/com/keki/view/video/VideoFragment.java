package ke.co.keki.com.keki.view.video;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.keki.com.keki.R;
import ke.co.keki.com.keki.model.pojo.Steps;
import ke.co.keki.com.keki.utils.NotificationChannelSupport;
import ke.co.keki.com.keki.utils.PastryConstants;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Video Fragment Class
 * Handles Videos and Notification For Media
 */
public class VideoFragment extends Fragment {


    @BindView(R.id.fl_player_container)
    FrameLayout playerContainer;
    @BindView(R.id.video_player_view)
    PlayerView mPlayerView;
    @BindView(R.id.tv_description)
    TextView descriptionTextView;
    private static final String TAG = VideoStepsActivity.class.getSimpleName();
    private SimpleExoPlayer player;
    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private VideoFragment.ComponentListener componentListener;
    private static MediaSessionCompat mediaSessionCompat;
    private PlaybackStateCompat.Builder mediaPlaybackState;
    private static final String NOTIFICATION_CHANNEL_ID = "music_notification";
    private NotificationManager notificationManager;
    private String videoLink;
    private String description;
    private Steps currentSteps;

    public VideoFragment() {

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoLink = videoUrl;
    }

    public void setCurrentSteps(Steps currentSteps) {
        this.currentSteps = currentSteps;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public String getDescription() {
        return description;
    }

    public Steps getCurrentSteps() {
        return currentSteps;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        ButterKnife.bind(this, view);
        //If Saved instance state has asomething in it
        if (savedInstanceState != null) {
            videoLink = savedInstanceState.getString(PastryConstants.VIDEO_URL);
            currentSteps = Parcels.unwrap(savedInstanceState.getParcelable(PastryConstants.CURRENT_STEP));
            description = savedInstanceState.getString(PastryConstants.STEP_DESCRIPTION);
        }
        if (getVideoLink() == null) {
            //If it has no link Make Player Disappear
            playerContainer.setVisibility(View.GONE);
        }
        //Set Step Description
        descriptionTextView.setText(getDescription());
        componentListener = new VideoFragment.ComponentListener();
        mediaSessionCompat = new MediaSessionCompat(inflater.getContext(), TAG);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaPlaybackState = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                );
        mediaSessionCompat.setPlaybackState(mediaPlaybackState.build());
        mediaSessionCompat.setMediaButtonReceiver(null);
        mediaSessionCompat.setCallback(new VideoFragment.MediaCallback());
        mediaSessionCompat.setActive(true);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save State On Rotate
        outState.putString(PastryConstants.VIDEO_URL, getVideoLink());
        outState.putParcelable(PastryConstants.CURRENT_STEP, Parcels.wrap(getCurrentSteps()));
        outState.putString(PastryConstants.STEP_DESCRIPTION, getDescription());
    }

    private void initializePlayer(String videoLink) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(getActivity(),
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
        }
        mPlayerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        if (videoLink != null && !videoLink.equals("")) {
            Uri uri = Uri.parse(videoLink);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, false);
        } else {
            Log.d(TAG, "Empty Link");
        }
        player.addListener(componentListener);
        player.addAnalyticsListener(componentListener);
    }

    /**
     * @param uri uri to media source to be built in extractor media source factory
     * @return returns the media source to be played
     */
    private MediaSource buildMediaSource(Uri uri) {
        final String USER_AGENT = "keki";
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(USER_AGENT)).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(videoLink);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            if (videoLink == null || videoLink.equals("")) {
                playerContainer.setVisibility(View.GONE);
            } else {
                initializePlayer(videoLink);
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaSessionCompat.setActive(false);
    }

    void displayNotification(Steps steps, PlaybackStateCompat state) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), NOTIFICATION_CHANNEL_ID);
        NotificationChannelSupport notificationChannelSupport = new NotificationChannelSupport();
        notificationChannelSupport.createNotificationChannel(getContext(), NOTIFICATION_CHANNEL_ID);
        int icon;
        String play_pause;
        if (state.getState() == PlaybackStateCompat.STATE_PLAYING) {
            icon = R.drawable.exo_controls_pause;
            play_pause = getString(R.string.pause);
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = getString(R.string.play);
        }

        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon,
                play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(getActivity(), PlaybackStateCompat.ACTION_PLAY_PAUSE));
        NotificationCompat.Action restartAction = new NotificationCompat.Action(
                R.drawable.exo_icon_previous,
                getString(R.string.previous),
                MediaButtonReceiver.buildMediaButtonPendingIntent(getActivity(), PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));
        NotificationCompat.Action restartActionNext = new NotificationCompat.Action(
                R.drawable.exo_icon_next,
                getString(R.string.next),
                MediaButtonReceiver.buildMediaButtonPendingIntent(getActivity(), PlaybackStateCompat.ACTION_SKIP_TO_NEXT));

        PendingIntent contentPendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(getActivity(), VideoStepsActivity.class), 0);

        builder.setContentTitle("" + steps.getId())
                .setContentText(steps.getShortDescription().trim())
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.exo_notification_small_icon)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(restartAction)
                .addAction(playPauseAction)
                .addAction(restartActionNext)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSessionCompat.getSessionToken())
                        .setShowActionsInCompactView(0, 1, 2));
        notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    private void releasePlayer() {
        if (player != null) {
            if (notificationManager != null) {
                notificationManager.cancelAll();
            }
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.removeListener(componentListener);
            player.removeVideoListener(null);
            player.removeAnalyticsListener(componentListener);
            player.release();
            player = null;
        }
    }


    private class ComponentListener implements Player.EventListener, AnalyticsListener {

        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady,
                                         int playbackState) {
            if (playbackState == Player.STATE_READY && playWhenReady) {
                mediaPlaybackState.setState(PlaybackStateCompat.STATE_PLAYING, player.getContentPosition(), 1f);

            } else if (playbackState == Player.STATE_READY) {
                mediaPlaybackState.setState(PlaybackStateCompat.STATE_PAUSED, player.getContentPosition(), 1f);
            }
            mediaSessionCompat.setPlaybackState(mediaPlaybackState.build());
            displayNotification(currentSteps, mediaPlaybackState.build());
        }
    }

    private class MediaCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPause() {
            super.onPause();
            player.setPlayWhenReady(false);
        }

        @Override
        public void onPlay() {
            super.onPlay();
            player.setPlayWhenReady(true);
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            player.seekTo(0);
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            player.seekTo(0);
        }
    }

    public static class MediaButtonNotification extends BroadcastReceiver {
        public MediaButtonNotification() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mediaSessionCompat, intent);
        }
    }


}
