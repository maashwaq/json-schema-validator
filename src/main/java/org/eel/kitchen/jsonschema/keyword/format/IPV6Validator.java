/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.keyword.format;

import org.codehaus.jackson.JsonNode;
import org.eel.kitchen.jsonschema.ValidationReport;

import java.net.Inet6Address;
import java.net.UnknownHostException;

/**
 * Validator for the "ipv6" format specification
 *
 * <p>This uses {@link Inet6Address#getByName(String)} to validate,
 * which means we must ensure this is a "numerical" IPv6 address before
 * proceeding. Easy enough: {@code :} is not valid in host names,
 * but it required for IPv6 addresses...</p>
 */
public final class IPV6Validator
    extends AbstractFormatValidator
{
    public IPV6Validator(final ValidationReport report, final JsonNode node)
    {
        super(report, node);
    }

    @Override
    public ValidationReport validate()
    {
        try {
            final String ipaddr = node.getTextValue();
            if (ipaddr.indexOf(':') == -1)
                throw new UnknownHostException();
            Inet6Address.getByName(ipaddr);
        } catch (UnknownHostException ignored) {
            report.addMessage("string is not a valid IPv6 address");
        }

        return report;
    }
}