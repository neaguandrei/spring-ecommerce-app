package aneagu.proj.service;


import aneagu.proj.models.dto.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public interface ResourceAssemblerService {

    CollectionModel<PaymentDto> assemblePaymentsResource(List<PaymentDto> list);
}
