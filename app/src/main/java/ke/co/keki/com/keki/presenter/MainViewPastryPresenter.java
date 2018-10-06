package ke.co.keki.com.keki.presenter;


import ke.co.keki.com.keki.model.Pastry;

public class MainViewPastryPresenter {

    private Pastry pastry;
    private View view;

    public MainViewPastryPresenter(View view) {
        this.pastry = new Pastry();
        this.view = view;
    }

    public void displayCardImage(String imageUrl) {

        view.showRecipeCards();
    }

    public interface View {
        void showRecipeCards();

        void showOnErrorView();

        void showLoadAnimation();

        void showExitAnimation();
    }
}
