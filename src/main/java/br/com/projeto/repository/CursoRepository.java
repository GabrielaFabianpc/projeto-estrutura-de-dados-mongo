package br.com.projeto.repository;

import org.bson.*;
import com.mongodb.client.*;
import br.com.projeto.model.Curso;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class CursoRepository extends BaseRepository<Curso> {
    public CursoRepository(MongoDatabase db) {
        super(db, "cursos");
    }

    protected Document toDoc(Curso x) {
        Document d = new Document("nome", x.nome());
        if (x.id() != null)
            d.put("_id", oid(x.id()));
        return d;
    }

    protected Curso fromDoc(Document d) {
        return new Curso(sid(d), d.getString("nome"));
    }

    public boolean update(String id, String nome) {
        try {
            return col.updateOne(eq("_id", oid(id)), set("nome", nome)).getMatchedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
