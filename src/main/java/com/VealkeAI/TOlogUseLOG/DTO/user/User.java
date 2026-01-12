package com.VealkeAI.TOlogUseLOG.DTO.user;

import java.util.List;

public interface User {
    Long tgId();
    List<Long> taskIdList();
    Integer shiftUTC();
}
