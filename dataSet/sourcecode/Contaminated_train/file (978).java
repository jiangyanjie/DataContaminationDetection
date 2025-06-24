// Converter.java, created on Jan 19, 2013
package eu.fabiostrozzi.chirp.service;

import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.fabiostrozzi.chirp.dao.ChirpUserEntity;
import eu.fabiostrozzi.chirp.dao.UserEntity;
import eu.fabiostrozzi.chirp.rest.Chirp;
import eu.fabiostrozzi.chirp.rest.User;

/**
 * Converts Chirp's entities from one layer to another.
 * 
 * @author fabio
 */
@Component
public class Converter {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(Converter.class);

    /**
     * @param data
     * @return
     */
    public List<User> restUsersOf(List<UserEntity> data) {
        ArrayList<User> users = new ArrayList<>();
        for (UserEntity ud : data)
            users.add(restUserOf(ud));
        return users;
    }

    /**
     * @param ud
     * @return
     */
    private User restUserOf(UserEntity ud) {
        User u = new User();
        u.setFirstName(ud.getFirstName());
        u.setLastName(ud.getLastName());
        u.setUsername(ud.getUsername());
        return u;
    }

    /**
     * @param data
     * @return
     */
    public List<Chirp> restChirpsOf(List<ChirpUserEntity> data) {
        ArrayList<Chirp> chirps = new ArrayList<>();
        for (ChirpUserEntity cud : data)
            chirps.add(restChirpOf(cud));
        return chirps;
    }

    /**
     * @param cud
     * @return
     */
    private Chirp restChirpOf(ChirpUserEntity cud) {
        Chirp c = new Chirp();
        c.setId(valueOf(cud.getChirp().getId()));
        c.setCreated(cud.getChirp().getCreated());
        c.setContent(cud.getChirp().getContent());
        c.setUser(restUserOf(cud.getUser()));
        return c;
    }

}
