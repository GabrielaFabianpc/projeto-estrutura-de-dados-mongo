package br.com.projeto.repository;

import org.bson.*;
import com.mongodb.client.*;
import br.com.projeto.model.Turma;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class TurmaRepository extends BaseRepository<Turma> {
    public TurmaRepository(MongoDatabase db) {
        super(db, "turmas");
    }

    protected Document toDoc(Turma x) {
        Document d = new Document("nome", x.nome());
        if (x.id() != null)
            d.put("_id", oid(x.id()));
        return d;
    }

    protected Turma fromDoc(Document d) {
        return new Turma(sid(d), d.getString("nome"));
    }

    public boolean update(String id, String nome) {
        try {
            return col.updateOne(eq("_id", oid(id)), set("nome", nome)).getMatchedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
