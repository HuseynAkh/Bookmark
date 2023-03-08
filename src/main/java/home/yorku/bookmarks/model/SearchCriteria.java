package home.yorku.bookmarks.model;

public class SearchCriteria {
    String type;
    String searchKey;
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
