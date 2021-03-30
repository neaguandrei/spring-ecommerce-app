package com.fmi.payment.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.payment.controller.PaymentController;
import com.fmi.payment.dto.PaymentDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResourceAssemblerService {

    public CollectionModel<PaymentDto> assemblePaymentsResource(List<PaymentDto> list) {
        list.forEach(paymentDto -> {
            Long id = paymentDto.getId();
            try {
                Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentController.class).getPayment(id)).withSelfRel();
                paymentDto.add(link);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });

        return new CollectionModel<>(list, WebMvcLinkBuilder.linkTo(PaymentController.class).withSelfRel());
    }
}
