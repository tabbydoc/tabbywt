package webreduce.extraction;

import org.jsoup.nodes.Document;
import webreduce.data.Dataset;

import java.io.IOException;
import java.util.List;

public interface ExtractionAlgorithm {

	public abstract List<Dataset> extract(Document doc,
			DocumentMetadata metadata) throws IOException, InterruptedException;

	public abstract StatsKeeper getStatsKeeper();

}