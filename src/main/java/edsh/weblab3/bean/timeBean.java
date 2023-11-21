package edsh.weblab3.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named
@ApplicationScoped
public class timeBean {
    private LocalDateTime dateTime;

    public LocalDateTime getDateTime() {
        dateTime = LocalDateTime.now();
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTimeStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return getDateTime().format(formatter);
    }
}
