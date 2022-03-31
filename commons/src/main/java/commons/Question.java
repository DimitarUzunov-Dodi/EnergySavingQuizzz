package commons;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.Duration;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Question {

    // Countdown duration (can be overridden in non-abstract child classes)
    protected Duration duration = Duration.ofSeconds(10);

    public abstract String displayText();

    public abstract int getQuestionType();

    public Duration getDuration() {
        return duration;
    }
}
