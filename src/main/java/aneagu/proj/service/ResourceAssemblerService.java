package aneagu.proj.service;

import aneagu.proj.controller.PaymentController;
import aneagu.proj.models.dto.PaymentDto;
import aneagu.proj.models.exception.NotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ResourceAssemblerService {

    public CollectionModel<PaymentDto> assemblePaymentsResource(List<PaymentDto> list) {
        list.forEach(paymentDto -> {
            Long id = paymentDto.getId();
            try {
                Link link = linkTo(methodOn(PaymentController.class).getPayment(id)).withSelfRel();
                paymentDto.add(link);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });

        return new CollectionModel<>(list, linkTo(PaymentController.class).withSelfRel());
    }
}
