package com.firesoftitan.play.fmtablist.mystuff;

import java.util.UUID;

/**
 * Created by Daniel on 12/19/2016.
 */
public class teleportpro {
    public UUID usernameA, usernameB;
    public long timetp;
    public boolean protect = false;

    public teleportpro(UUID A, UUID B, Long tTime)
    {
        usernameA = A;
        usernameB = B;
        timetp = tTime;

    }
}
