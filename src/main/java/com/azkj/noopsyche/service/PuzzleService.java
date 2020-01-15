package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Assemble;
import com.azkj.noopsyche.entity.Groups;
import com.azkj.noopsyche.entity.People;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.util.List;

public interface PuzzleService {
    void addPuzzle(String token, Integer assembleId) throws NoopsycheException, ParseException;

    void JoinGroup(Integer gid, String token) throws NoopsycheException;

    PageInfo<People> findGroup(String token, Integer page, Integer limit, Integer status) throws NoopsycheException;

    PageInfo<Groups> findSpuGroup(Integer spuid, Integer page, Integer limit) throws NoopsycheException;

    Groups findPuzzleByGid(Integer gid);

    List<Assemble> findAssembleBySpuid(Integer spuid) throws NoopsycheException;
}
