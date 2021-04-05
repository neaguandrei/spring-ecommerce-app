package com.fmi.payment.assembler;

import com.fmi.api.payment.PaymentResponseResource;
import com.fmi.common.exception.NotFoundException;
import com.fmi.payment.controller.RestPaymentController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ResourceAssembler {

    public CollectionModel<PaymentResponseResource> assemblePaymentsResource(List<PaymentResponseResource> list) {
        list.forEach(paymentDto -> {
            Long id = paymentDto.getId();
            try {
                Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestPaymentController.class).getPayment(id)).withSelfRel();
                paymentDto.add(link);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });

        return new CollectionModel<>(list, WebMvcLinkBuilder.linkTo(RestPaymentController.class).withSelfRel());
    }
}
