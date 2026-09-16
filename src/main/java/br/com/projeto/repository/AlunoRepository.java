package br.com.projeto.repository;

import org.bson.types.ObjectId;
import java.util.*;
import org.bson.*;
import com.mongodb.client.*;
import br.com.projeto.model.Aluno;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class AlunoRepository extends BaseRepository<Aluno> {
    public AlunoRepository(MongoDatabase db) {
        super(db, "alunos");
    }

    protected Document toDoc(Aluno x) {
        Document d = new Document("nome", x.nome()).append("ra", x.ra()).append("turmaId", oid(x.turmaId()));
        if (x.id() != null)
            d.put("_id", oid(x.id()));
        return d;
    }

    protected Aluno fromDoc(Document d) {
        ObjectId t = d.getObjectId("turmaId");
        return new Aluno(sid(d), d.getString("nome"), d.getString("ra"), t == null ? null : t.toHexString());
    }

    public boolean update(String id, String nome, String ra, String turmaId) {
        try {
            return col
                    .updateOne(eq("_id", oid(id)),
                            combine(set("nome", nome), set("ra", ra), set("turmaId", oid(turmaId))))
                    .getMatchedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Aluno> findByTurma(String turmaId) {
        List<Aluno> r = new ArrayList<>();
        try {
            col.find(eq("turmaId", oid(turmaId))).forEach(d -> r.add(fromDoc(d)));
        } catch (Exception e) {
        }
        return r;
    }
}
