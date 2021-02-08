package com.springbank.banckacc.cmd.api.controllers;

import com.springbank.banckacc.cmd.api.commands.DepositFoundsCommand;
import com.springbank.banckacc.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/depositFounds")
public class DepositFundsController {
    private final CommandGateway commandGateway;

    public DepositFundsController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id , @Valid @RequestBody DepositFoundsCommand command){
        try{
            command.setId(id);
            commandGateway.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds successfully deposited"), HttpStatus.OK);
        }catch (Exception e){
            String safeErrorMessage="Error while processing request to deposit founds intobank account for id -" +id;
            System.out.println(e.toString());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
