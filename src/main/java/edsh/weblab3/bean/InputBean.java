package edsh.weblab3.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class InputBean implements Serializable {
    private Double x;
    private Double y;
    private Double r;

    public void validateX(FacesContext facesContext,
                                UIComponent uiComponent, Object o) {
        double x = (Double)o;
        if(x < -5 || x > 5) {
            throw new ValidatorException(new FacesMessage("X must be in (-5; 5)"));
        }
    }

    public void validateY(FacesContext facesContext,
                          UIComponent uiComponent, Object o) {
        double y = (Double)o;
        if(y < -5 || y > 3) {
            throw new ValidatorException(new FacesMessage("Y must be in (-5; 3)"));
        }
    }

    public void validateR(FacesContext facesContext,
                          UIComponent uiComponent, Object o) {
        double r = (Double)o;
        if(r < 1 || r > 3) {
            throw new ValidatorException(new FacesMessage("R must be in (1; 3)"));
        }
    }

    
    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts()
                .add("makeGraph(" + r + ");");
    }
}
