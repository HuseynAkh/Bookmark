package home.yorku.bookmarks.controller;

import javafx.scene.Scene;
import javafx.scene.control.Tab;

public class InitController {

    private BookmarkController bookmark;
    public InitController(BookmarkController bookmark){
        this.bookmark = bookmark;
    }
    protected void initialize(){
        bookmark.myBookList.setItems(bookmark.bookList);
        bookmark.recommendation.setItems(bookmark.recos);
        bookmark.upNextList.setItems(bookmark.futureList);
        bookmark.myMovieList.setItems(bookmark.movieList);
        bookmark.ML_myBookList.setItems(bookmark.MLbookList);
        bookmark.ML_myMovieList.setItems(bookmark.MLmovieList);
        bookmark.favourite_books.setItems(bookmark.MLfavBooks);
        bookmark.favourite_movies.setItems(bookmark.MLfavMovies);

        Scene scene = bookmark.anchorPane.getScene();
        if (scene != null) {
            bookmark.sceneHeight = scene.getHeight();
            bookmark.sceneWidth = scene.getWidth();
        }

        // All dynamic login box layouts
        bookmark.anchorPane.sceneProperty().addListener((observable, oldScene, newScene) -> {

            if (newScene != null) {
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    bookmark.sceneHeight = newHeight.doubleValue();
                    bookmark.LoginBox.setLayoutY((bookmark.sceneHeight / 2) - (20 + bookmark.LoginBox.getHeight() / 2)); // 20 for padding between boxes
                    bookmark.RecoBox.setLayoutY((bookmark.sceneHeight / 2) - (20 + bookmark.RecoBox.getHeight() / 2));

                });

                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    bookmark.sceneWidth = newWidth.doubleValue();
                    bookmark.LoginBox.setLayoutX((bookmark.sceneWidth / 2) - (bookmark.LoginBox.getWidth() / 2));
                    bookmark.RecoBox.setLayoutX((bookmark.sceneWidth / 2) - (bookmark.RecoBox.getWidth() / 2));

                });
            }

        });

        bookmark.FavouriteBookTab.setOnSelectionChanged(event -> {
            if (bookmark.FavouriteBookTab.isSelected()) {
                bookmark.removeBookFavBtn.setVisible(true);
                bookmark.removeBook.setVisible(false);
                bookmark.addBookToFav.setVisible(false);
                bookmark.bookSort.setVisible(false);

            } else {
                bookmark.removeBookFavBtn.setVisible(false);
                bookmark.removeBook.setVisible(true);
                bookmark.addBookToFav.setVisible(true);
                bookmark.bookSort.setVisible(true);
            }
        });

        bookmark.FavouriteMovieTab.setOnSelectionChanged(event -> {
            if (bookmark.FavouriteMovieTab.isSelected()) {
                bookmark.removeMovieFavBtn.setVisible(true);
                bookmark.removeMovie.setVisible(false);
                bookmark.addMovieToFav.setVisible(false);
                bookmark.movieSort.setVisible(false);

            } else {
                bookmark.removeMovieFavBtn.setVisible(false);
                bookmark.removeMovie.setVisible(true);
                bookmark.addMovieToFav.setVisible(true);
                bookmark.movieSort.setVisible(true);
            }
        });

        // Initialize locked Panes
        setPane();

        // Initialize search options for Books and Movies
        bookmark.searchType.setOnAction(event -> {

            if (bookmark.searchType.getValue().equals("Movies")) {
                bookmark.searchBy.setItems(bookmark.moviesSearchOptions);
                bookmark.searchBy.setValue("Search by");
            } else if (bookmark.searchType.getValue().equals("Books")) {
                bookmark.searchBy.setItems(bookmark.booksSearchOptions);
                bookmark.searchBy.setValue("Search by");
            }

        });
    }

    protected void setPane() {
        for (Tab tab : bookmark.tabPane.getTabs()) {

            if (!tab.getId().equals("LoginPane")) {
                tab.setDisable(true);
            }

        }

        Tab logoutTab = bookmark.tabPane.getTabs().stream()
                .filter(tab -> tab.getId().equals("LogoutPane"))
                .findFirst()
                .orElse(null);
        bookmark.removedTabs.add(logoutTab);
        bookmark.tabPane.getTabs().remove(logoutTab);
    }
}
