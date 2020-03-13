package aneagu.proj.controller;

import aneagu.proj.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;


    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @PostMapping
//    public ResponseEntity<?> postAnswer(@RequestBody @NotNull @Valid CommentDto commentDto) {
//        commentService.save(commentDto);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CommentDto>> getAnswersForQuestion(@PathVariable(value = "answerId") Long answerId) {
//        List<CommentDto> resultList = commentService.findAllByAnswerId(answerId);
//        return ResponseEntity.ok(resultList);
//    }
//
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<?> updateAnswer(@PathVariable("id") Long id, @RequestBody @NotNull @Valid CommentDto commentDto) throws RenException {
//        commentService.update(id, commentDto);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<?> deleteAnswer(@PathVariable("id") Long id) throws RenException {
//        commentService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
