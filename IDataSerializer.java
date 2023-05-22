import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface IDataSerializer {
    void Save(DataOutputStream dos);
    void Load(DataInputStream dis);
}