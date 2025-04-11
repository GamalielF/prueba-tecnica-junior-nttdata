-- Crear clientes
INSERT INTO persona (id, nombre, genero, edad, identificacion, direccion, telefono) VALUES
                                                                                        (1, 'Jose Lema', 'Masculino', 30, '123456789', 'Otavalo sn y principal', '098254785'),
                                                                                        (2, 'Marianela Montalvo', 'Femenino', 28, '987654321', 'Amazonas y NNUU', '097548965'),
                                                                                        (3, 'Juan Osorio', 'Masculino', 35, '456789123', '13 junio y Equinoccial', '098874587');

INSERT INTO cliente (id, client_id, contrasena, estado) VALUES
                                                            (1, 'CL001', '1234', true),
                                                            (2, 'CL002', '5678', true),
                                                            (3, 'CL003', '1245', true);

-- Crear cuentas
INSERT INTO cuenta (id, numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES
                                                                                           (1, '478758', 'Ahorro', 2000, true, 1),
                                                                                           (2, '225487', 'Corriente', 100, true, 2),
                                                                                           (3, '495878', 'Ahorros', 0, true, 3),
                                                                                           (4, '496825', 'Ahorros', 540, true, 2);