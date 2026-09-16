package br.com.projeto.repository;

import java.util.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.eq;

public abstract class BaseRepository<T> {
    protected final MongoCollection<Document> col;

    protected BaseRepository(MongoDatabase db, String nome) {
        col = db.getCollection(nome);
    }

    protected abstract Document toDoc(T obj);

    protected abstract T fromDoc(Document d);

    public T create(T obj) {
        Document d = toDoc(obj);
        if (!d.containsKey("_id") || d.get("_id") == null)
            d.put("_id", new ObjectId());
        col.insertOne(d);
        return fromDoc(d);
    }

    public List<T> findAll() {
        List<T> r = new ArrayList<>();
        col.find().forEach(d -> r.add(fromDoc(d)));
        return r;
    }

    public Optional<T> findById(String id) {
        try {
            Document d = col.find(eq("_id", new ObjectId(id))).first();
            return Optional.ofNullable(d).map(this::fromDoc);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean delete(String id) {
        try {
            return col.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    protected ObjectId oid(String id) {
        return id == null || id.isBlank() ? null : new ObjectId(id);
    }

    protected String sid(Document d) {
        return d.getObjectId("_id").toHexString();
    }
}
