package com.mfouad.batchPro;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "sechdule")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchedularConfig {

	private int pageSize;
	private int chunkSize;
	private long afterChunkWaitTime;

}
