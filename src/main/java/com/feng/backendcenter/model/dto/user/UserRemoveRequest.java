package com.feng.backendcenter.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author bood
 * @since 2024/10/14 16:54
 */
@Data
public class UserRemoveRequest implements Serializable {

    /**
     * 用户id
     */
    private Long id;
    @Serial
    private static final long serialVersionUID = 1L;
}
