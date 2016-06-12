/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.server.security.enterprise.auth;

import org.neo4j.helpers.Service;
import org.neo4j.kernel.api.exceptions.KernelException;
import org.neo4j.kernel.api.proc.CallableProcedure;
import org.neo4j.kernel.api.security.AccessMode;
import org.neo4j.kernel.api.security.AuthSubject;
import org.neo4j.kernel.impl.enterprise.EnterpriseProceduresProvider;
import org.neo4j.kernel.impl.proc.Procedures;

@Service.Implementation( EnterpriseProceduresProvider.class )
public class ProceduresProvider extends Service implements EnterpriseProceduresProvider
{
    public ProceduresProvider()
    {
        super( "procedures-provider" );
    }

    @Override
    public void registerProcedures( Procedures procedures )
    {
        try
        {
            procedures.registerComponent( AccessMode.class, ctx -> ctx.get( CallableProcedure.Context.ACCESS_MODE ) );
            procedures.registerComponent( AuthSubject.class, ctx -> ctx.get( CallableProcedure.Context.AUTH_SUBJECT ) );
            procedures.register( AuthProcedures.class );
        }
        catch ( KernelException e )
        {
            throw new RuntimeException( e );
        }
    }
}