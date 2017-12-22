package com.atherys.core;

import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.io.IOException;

@ConfigSerializable
public final class CoreConfig {

    @Setting
    public String mongoHost = "localhost";

    @Setting
    public int mongoPort = 27017;

    @Setting
    public String mongoDatabase = "core_Database";

    @Setting
    public String mongoUserDB = "user_database";

    @Setting
    public String mongoUsername = "username";

    @Setting
    public String mongoPassword = "password";

    private ObjectMapper<CoreConfig>.BoundInstance configMapper;
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    public CoreConfig( ConfigurationLoader<CommentedConfigurationNode> loader ) {
        this.loader = loader;
        try {
            this.configMapper = ObjectMapper.forObject(this);
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        this.load();
    }

    public void save() {
        try {
            SimpleConfigurationNode out = SimpleConfigurationNode.root();
            this.configMapper.serialize(out);
            this.loader.save(out);
        } catch (ObjectMappingException | IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            this.configMapper.populate( this.loader.load() );
        } catch (ObjectMappingException | IOException e) {
            e.printStackTrace();
        }
    }
}
