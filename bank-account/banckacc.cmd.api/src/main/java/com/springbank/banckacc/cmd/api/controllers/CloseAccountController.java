package com.springbank.banckacc.cmd.api.controllers;

import com.springbank.banckacc.cmd.api.commands.CloseAccountCommand;
import com.springbank.banckacc.cmd.api.dto.OpenAccountResponse;
import com.springbank.banckacc.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/closeBankAccount")
public class CloseAccountController {

    private final CommandGateway commandGateway;

    public CloseAccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id){
        try {
            CloseAccountCommand command = CloseAccountCommand.builder().id(id).build();
            commandGateway.send(command);
            return new ResponseEntity<>(new BaseResponse("Bank account successfully closed."), HttpStatus.OK);
        }catch (Exception e){
            String safeErrorMessage="Error while processing request to close  bank account for id -" +id;
            System.out.println(e.toString());

            return new ResponseEntity<>(new OpenAccountResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
