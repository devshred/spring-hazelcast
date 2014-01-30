package org.devshred.hazelcast.persistence;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Share implements DataSerializable {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	private String mic;

	private String name;

	private int quote;

	protected Share() {
	}

	public Share(final String mic) {
		this.mic = mic;
	}

	public Share(final String mic, final String name, final int quote) {
		this.mic = mic;
		this.name = name;
		this.quote = quote;
	}

	public String getMic() {
		return mic;
	}

	public String getName() {
		return name;
	}

	public int getQuote() {
		return quote;
	}

	public void setQuote(final int quote) {
		this.quote = quote;
	}

	@Override
	public void writeData(final ObjectDataOutput out) throws IOException {
		out.writeUTF(name);
		out.writeUTF(mic);
		out.writeInt(quote);
	}

	@Override
	public void readData(final ObjectDataInput in) throws IOException {
		name = in.readUTF();
		mic = in.readUTF();
		quote = in.readInt();
	}
}
