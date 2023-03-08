package home.yorku.bookmarks.model;

public class SearchCriteria {
    //Book or movie - more can be added if needed
    String type;

    //mode of searching(ex. genre/actor/title)
    String searchKey;

    //user input(ex. horror/nic cage/the family man)
    String value;

    public SearchCriteria(String type, String searchKey, String value) {
        this.type = type;
        this.searchKey = searchKey;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public String getValue() {
        return value;
    }
}
