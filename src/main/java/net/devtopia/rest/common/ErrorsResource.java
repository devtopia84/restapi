package net.devtopia.rest.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import net.devtopia.rest.index.IndexController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ErrorsResource extends ResourceSupport {
    @JsonUnwrapped
    private Errors errors;

    public ErrorsResource(Errors errors) {
        this.errors = errors;
        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }

    public Errors getErrors() {
        return errors;
    }
}


//public class ErrorsResource extends Resource<Errors> {
//    public ErrorsResource(Errors content, Link... links) {
//        super(content, links);
//        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
//    }
//}
