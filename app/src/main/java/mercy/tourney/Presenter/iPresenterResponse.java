package mercy.tourney.Presenter;

import mercy.tourney.Model.Basic.Response;

/**
 * Created by Andrianto on 11/25/2016.
 */
public interface iPresenterResponse {
    public void doSuccess(Response response);

    public void doFail(String message);

    public void doConnectionError(int message);
}
