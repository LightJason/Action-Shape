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

// -----
// agent for testing math actions
// @iteration 2
// @testcount 3
// -----

// initial-goal
!test.

/**
 * base test
 */
+!test <-
    !testshape
.


/**
 * test shape
 */
+!testshape <-
    .test/result( .math/shape/inrectangle( 0,0, 4,5,   2,1 ), "in-shape has been failed" );
    .test/result( .math/shape/incircle( 2,2,1,  2,1 ), "in-circle has been failed" );
    .test/result( .math/shape/intriangle( 350,320,  25,375,  40,55,    160,270 ), "in-triangle has been failed" );

    .test/print("shapes (in) executed completly")
.