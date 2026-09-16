package br.com.projeto.repository;

import java.util.*;
import org.bson.*;
import org.bson.types.ObjectId;
import com.mongodb.client.*;
import br.com.projeto.model.Desafio;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class DesafioRepository extends BaseRepository<Desafio> {
    public DesafioRepository(MongoDatabase db) {
        super(db, "desafios");
    }

    private List<ObjectId> ids(List<String> xs) {
        return xs == null ? new ArrayList<>()
                : xs.stream().filter(s -> s != null && !s.isBlank()).map(ObjectId::new).toList();
    }

    protected Document toDoc(Desafio x) {
        Document d = new Document("nome", x.nome()).append("cursoId", oid(x.cursoId()))
                .append("periodoId", oid(x.periodoId())).append("turmaId", oid(x.turmaId()))
                .append("alunoIds", ids(x.alunoIds()));
        if (x.id() != null)
            d.put("_id", oid(x.id()));
        return d;
    }

    protected Desafio fromDoc(Document d) {
        List<ObjectId> a = d.getList("alunoIds", ObjectId.class, new ArrayList<>());
        return new Desafio(sid(d), d.getString("nome"), d.getObjectId("cursoId").toHexString(),
                d.getObjectId("periodoId").toHexString(), d.getObjectId("turmaId").toHexString(),
                a.stream().map(ObjectId::toHexString).toList());
    }

    public boolean update(Desafio x) {
        try {
            return col.replaceOne(eq("_id", oid(x.id())), toDoc(x)).getMatchedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
