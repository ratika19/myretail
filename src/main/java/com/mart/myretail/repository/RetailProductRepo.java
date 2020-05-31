package com.mart.myretail.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import com.mart.myretail.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;

/** Repository class which connects to Datastax constellation cloud database and provides repository
 *  layer to read and write
 */
@Repository
public class RetailProductRepo implements RetailBaseRepo<Product> {

    private CqlSession session;

    @Value("${repo.connectbundle.path}")
    private String connectBundlePath;
    @Value("${repo.access.user}")
    private String userName;
    @Value("${repo.access.phrase}")
    private String accessPhrase;
    @Value("${repo.access.keyspace}")
    private String keyspace;

    public RetailProductRepo(){

    }

    @PostConstruct
    private void createCqlSession() {
        this.session = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(connectBundlePath))
                .withAuthCredentials(userName,accessPhrase)
                .withKeyspace(keyspace)
                .build();
    }

    public CqlSession getCqlSession() {
        return session;
    }

    public void setCqlSession(CqlSession session) {
        this.session = session;
    }


    @Override
    public Product findById(int id) {
        Product product = null;
        PreparedStatement preparedStatement = session.prepare(getQueryForFindById());
        BoundStatement boundStatement = preparedStatement.bind(id);
        ResultSet result = session.execute(boundStatement);
        if( null != result && result.iterator().hasNext()) {
            Row resultRow = result.one();
            product = new Product(resultRow.getInt("product_id"), resultRow.getString("product_code"),resultRow.getFloat("product_value"), resultRow.getString("currency_code"));
        }

        return product;
    }

    @Override
    public boolean save(Product product) {
        PreparedStatement preparedStatement = session.prepare(getQueryForUpdateProduct());
        BoundStatement boundStatement = preparedStatement.bind(product.getProductValue(),
                product.getCurrencyCode(),userName, Instant.now(),product.getProductId(),product.getProductCode());
        ResultSet result = session.execute(boundStatement);
        return true;
    }



    private String getQueryForFindById(){
        Select selectQ = QueryBuilder.selectFrom("product").column("product_id")
                .column("product_code")
                .column("product_value")
                .column("currency_code")
                .whereColumn("product_id").isEqualTo(QueryBuilder.bindMarker());
        return selectQ.toString();
    }

   private String getQueryForUpdateProduct(){
        Update updateQ = QueryBuilder.update("product")
                .setColumn("product_value", QueryBuilder.bindMarker())
                .setColumn("currency_code", QueryBuilder.bindMarker())
                .setColumn("updated_by", QueryBuilder.bindMarker())
                .setColumn("update_date", QueryBuilder.bindMarker())
                .whereColumn("product_id").isEqualTo(QueryBuilder.bindMarker())
                .whereColumn("product_code").isEqualTo(QueryBuilder.bindMarker());
        return updateQ.toString();
    }
}
