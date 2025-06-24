package org.ctdog.generator.impl;

import org.ctdog.core.Action;
import org.ctdog.generator.ConfigurationGenerator;
import org.ctdog.meta.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Component:Concurrent Tester
 * Description:Commands configuration generator implementation
 * Date: 12-7-17
 *
 * @author Andy.Ai
 */
public class ConfigurationGeneratorImpl implements ConfigurationGenerator {
    @Override
    @SuppressWarnings("unchecked")
    public Configuration generateConfiguration(String[] commands) throws ClassNotFoundException {
        Configuration configuration = new Configuration();
        for (int i = 0; i < commands.length; i++) {
            String command = commands[i];
            if ("-c".equals(command)) {
                configuration.setConcurrent(Integer.valueOf(commands[++i]));
            } else if ("-d".equals(command)) {
                configuration.setDuration(Long.valueOf(commands[++i]));
            } else if ("-i".equals(command)) {
                configuration.setIndex(Integer.valueOf(commands[++i]));
            } else if ("-t".equals(command)) {
                configuration.setTimeout(Integer.valueOf(commands[++i]));
            } else if ("-p".equals(command)) {
                String stringParameters = commands[++i];
                String[] tempArray = stringParameters.split(",");
                Map<String, Object> parameters = new HashMap<String, Object>(tempArray.length / 2);
                for (int j = 0; j < tempArray.length; j += 2) {
                    parameters.put(tempArray[j], tempArray[j + 1]);
                }
                configuration.setParameters(parameters);
            } else if ("-a".equals(command)) {
                Class<Action> action = (Class<Action>) Class.forName(commands[++i]);
                configuration.setAction(action);
            }
        }
        return configuration;
    }
}
