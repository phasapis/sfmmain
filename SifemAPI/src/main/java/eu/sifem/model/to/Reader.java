package eu.sifem.model.to;

import java.io.InputStream;
import java.util.List;

/**
 * @author swapnil/jbjares
 * Reader is interface to read multiple type of files
 */
@Deprecated
public interface Reader {
	public Object read(String file);

	public List<String[]> read(InputStream fileName);
}
