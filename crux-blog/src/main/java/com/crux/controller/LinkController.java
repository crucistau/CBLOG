package com.crux.controller;

import com.crux.entity.domain.ResponseResult;
import com.crux.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author crucistau
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/link")
public class LinkController {
    private final LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
