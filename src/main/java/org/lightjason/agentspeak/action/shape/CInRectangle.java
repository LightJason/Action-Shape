/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
 * # Copyright (c) 2015-19, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.agentspeak.action.shape;

import com.codepoetics.protonpack.StreamUtils;
import org.lightjason.agentspeak.action.IBaseAction;
import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.error.context.CExecutionIllegealArgumentException;
import org.lightjason.agentspeak.language.CCommon;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.fuzzy.IFuzzyValue;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * action check if a point within a rectangle.
 * The first four arguments descripes the rectangle
 * (left-upper corner x- / y-postion, right-bottom corner
 * x- / y-position), all other arguments will used
 * as tuples with x- / y-position and will be checked
 * to the rectangle
 *
 * {@code [In1|In2] = .math/shape/inrectangle( 10,100,  110,10,  40,55,  120,110 );}
 */
public final class CInRectangle extends IBaseAction
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 1128548897818830791L;
    /**
     * action name
     */
    private static final IPath NAME = namebyclass( CInRectangle.class, "math", "shape" );

    @Nonnull
    @Override
    public IPath name()
    {
        return NAME;
    }

    @Nonnegative
    @Override
    public int minimalArgumentNumber()
    {
        return 1;
    }

    @Nonnull
    @Override
    public Stream<IFuzzyValue<?>> execute( final boolean p_parallel, @Nonnull final IContext p_context,
                                           @Nonnull final List<ITerm> p_argument, @Nonnull final List<ITerm> p_return
    )
    {
        final List<Double> l_arguments = CCommon.flatten( p_argument )
                                                .map( ITerm::<Number>raw )
                                                .mapToDouble( Number::doubleValue )
                                                .boxed()
                                                .collect( Collectors.toList() );

        if ( l_arguments.size() < 6 )
            throw new CExecutionIllegealArgumentException(
                p_context,
                org.lightjason.agentspeak.common.CCommon.languagestring( this, "wrongargumentnumber" )
            );

        StreamUtils.windowed( l_arguments.stream().skip( 4 ), 2, 2 )
                   // check in order upper-left x-position <= x-value <= buttom-right x-position, upper-left y-position <= y-value <= buttom-right y-position
                   .map( i -> l_arguments.get( 0 ) <= i.get( 0 ) && i.get( 0 ) <= l_arguments.get( 2 )
                              && l_arguments.get( 1 ) <= i.get( 1 ) && i.get( 1 ) <= l_arguments.get( 3 )
                   )
                   .map( CRawTerm::of )
                   .forEach( p_return::add );

        return Stream.of();
    }

}
