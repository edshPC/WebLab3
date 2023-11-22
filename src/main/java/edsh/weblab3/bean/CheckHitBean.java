package edsh.weblab3.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named
@SessionScoped
public class CheckHitBean implements Serializable {
    private double x;
    private double y;
    private double r;
    private boolean result;
    private LocalDateTime dateTime;
    private double execTime;

    public static CheckHitBean makeCheckHit(InputBean input) {
        CheckHitBean res = new CheckHitBean();
        long startTime = System.nanoTime();
        res.x = input.getX();
        res.y = input.getY();
        res.r = input.getR();
        res.result = checkHit(res.x, res.y, res.r);
        res.dateTime = LocalDateTime.now();
        res.execTime = (double) (System.nanoTime() - startTime) / 1000;
        res.drawPoint();
        return res;
    }

    private static boolean checkHit(double x, double y, double r) {
        if(x >= 0) {
            if(y > 0) return y <= r/2 - x; // up right
            else return x <= r && y >= -r; // down right
        } else {
            if(y > 0) return false; // up left
            else return x*x + y*y <= r*r/4; // down left
        }
    }

    public void drawPoint() {
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts()
                .add("drawPoint(" + x + ", " + y + ", " + result + ");");
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getResultStr() {
        return result? "Попал!" : "Промазал :(";
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTimeStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    public double getExecTime() {
        return execTime;
    }

    public String getExecTimeStr() {
        return execTime + "mks";
    }

    public void setExecTime(double execTime) {
        this.execTime = execTime;
    }
}
