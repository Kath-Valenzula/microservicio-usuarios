spool evidencia_conexion_bd\ddl_y_inserts_ok.txt
@db/script_oracle.sql
SELECT COUNT(*) USUARIOS   FROM USUARIOS;
SELECT COUNT(*) LABS       FROM LABORATORIOS;
SELECT COUNT(*) RESERVAS   FROM RESERVAS;
spool off
exit
