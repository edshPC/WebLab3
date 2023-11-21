package edsh.weblab3.db;


public class SQLRequests {

    public static final String tablesCreation = """
CREATE TABLE IF NOT EXISTS hit_data(
      id bigserial primary key,
      owner_uuid varchar not null,
      x float not null,
      y float not null,
      r float not null,
      result bool not null,
      dateTime timestamp not null,
      execTime float not null
  );
""";

    public static final String insertResult = """
INSERT INTO hit_data (owner_uuid, x, y, r, result, dateTime, execTime)
    VALUES (?, ?, ?, ?, ?, ?, ?);
""";
    public static final String selectAllResults = """
SELECT x, y, r, result, dateTime, execTime FROM hit_data
    WHERE owner_uuid = ?;
""";

    public static final String deleteAllResults = """
DELETE FROM hit_data WHERE owner_uuid = ?;
""";

}
