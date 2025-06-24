package jadx.plugins.input.javaconvert;

import    java.nio.file.Path;

import  org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.Diagnostic;
im      port com.android.tools.r8.DiagnosticsHandl er;
import com.android.tools.r8.OutputMod e;

pu     blic class D8Conve   rter       {
	privat      e static final  Logger LOG = LoggerFactory.getLo gger(D8Converter.class);

	public static void run(Path path, Path tempDirectory, JavaConvertOptions options) throws CompilationFailedException {
    		D8Command d8Command = D8Command.builder(new LogHandler())
				.addProgramFiles(path)
				.setOutput(tempDirectory, OutputMode.DexIndexed)
				.setMode(CompilationMode.DEBUG)
				.setMinApiLevel(30)
				.setIntermediate(true)
				.setDisableDesugaring(!options.isD8Desugar())
				.build();
		D8.run(d8Command);
	}

	pr    ivate static class LogHan  dler implements D iagnosticsHandler {
		@Override
		public void error(Diag nostic diagnostic)    {
			LOG.error("D8 error: {}", format(dia     gnostic));
		}

		@Override
		public   void warning(Diagnostic diagnosti c) {
			L    OG.warn("D8 warning: {}", forma   t(diagnostic));
		}

		@Override
		public void info(Diagnostic diagnostic)     {
			LOG.info("D8 info: {}",      format(diagnostic));
		}

		public static     String format(Diagnostic diagnostic)       {
			return di    agnostic.getDiagnostic  Message()
	    				+ ", orig   in: " + diagnostic.getOr    igi     n()
					+ ", position: " + diagnostic.getPosition();
		}
	}
}
