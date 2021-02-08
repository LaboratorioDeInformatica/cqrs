package com.springbank.banckacc.cmd.api.controllers;

import com.springbank.banckacc.cmd.api.commands.WithdrawFoundsCommand;
import com.springbank.banckacc.cmd.api.dto.OpenAccountResponse;
import com.springbank.banckacc.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {

    private final CommandGateway commandGateway;

    public WithdrawFundsController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> withdrawFounds(@PathVariable(value = "id") String id, @Valid @RequestBody WithdrawFoundsCommand command){
        try {
            command.setId(id);
            commandGateway.send(command);
            return new ResponseEntity<>(new BaseResponse("withdraw successfully "), HttpStatus.OK);
        }catch (Exception e){
            String safeErrorMessage="Error while processing request withdraw funds in a bank account for id -" +id;
            System.out.println(e.toString());

            return new ResponseEntity<>(new OpenAccountResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
