package edsh.weblab3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class RequestHandlerBean implements Serializable {
    @Inject
    private ResultsBean results;

    public void checkHit() {
        results.newResult();
    }

    public void clear() {
        results.clearResults();
    }
}
