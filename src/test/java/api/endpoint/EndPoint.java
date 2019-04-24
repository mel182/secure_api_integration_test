package api.endpoint;

public enum EndPoint
{
    LOGIN("POST:/auth/login"),
    ALL_ADMIN("GET:/admin/all"),
    CREATE_ADMIN("POST:/admin/create"),
    DELETE_ADMIN("DELETE:/admin/*"),
    CREATE_USER("POST:/user/create"),
    UPDATE_USER("PUT:/user/update/*"),
    ALL_USER("GET:/user/all"),
    DELETE_USER("DELETE:/user/*");

    private String endpoint;

    EndPoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getValue()
    {
        return this.endpoint;
    }
}