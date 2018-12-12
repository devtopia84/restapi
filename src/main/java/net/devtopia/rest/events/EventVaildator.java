package net.devtopia.rest.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Component
public class EventVaildator {
    public void vaildate(EventDto eventDto, Errors errors) {
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
            errors.rejectValue("basePrice", "wrongValue", "기본값이 문제가 있습니다.");
            errors.rejectValue("maxPrice", "wrongValue", "최대값이 문제가 있습니다.");
            errors.reject("wrongPrices", "Values of price are wrong.");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();

        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime())
                || endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())
                || endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("endEventDateTime", "wrongValue", "이벤트 종료일이 문제가 있습니다.");
        }

        // TODO beginEventDateTime
        // TODO closeEnrollmentDateTime
        // TODO beginEnrollmentDateTime
    }
}
