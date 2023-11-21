package edsh.weblab3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.LinkedList;

@Named
@SessionScoped
public class ResultsBean implements Serializable {
    private LinkedList<CheckHitBean> results = new LinkedList<>();
    @Inject
    InputBean input;

    public void newResult() {
        results.add(CheckHitBean.makeCheckHit(input));
    }

    public void clearResults() {
        results.clear();
    }

    public LinkedList<CheckHitBean> getResults() {
        return results;
    }

    public void setResults(LinkedList<CheckHitBean> results) {
        this.results = results;
    }
}
