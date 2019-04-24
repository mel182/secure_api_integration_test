package api.callback;

public interface HttpResponseCallback {
    void OnResponse(int responseCode, Object response);
    void OnRequestFailed(int responseCode, String error);
}
