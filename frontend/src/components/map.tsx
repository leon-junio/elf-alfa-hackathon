"use client"

import { ComposableMap, Geographies, Geography, Marker, ZoomableGroup } from "react-simple-maps"

const geoUrl =
    "https://raw.githubusercontent.com/deldersveld/topojson/master/world-countries.json"

interface MapChartProps {
    marks: {
        latitude: number,
        longitude: number,
    }[]
}

export default function MapChart(props: MapChartProps) {
    const { marks } = props

    return (
        <div>
            <ComposableMap projection="geoMercator">
                <ZoomableGroup center={[marks[0].longitude, marks[0].latitude]} zoom={2}>
                    <Geographies geography={geoUrl}>
                        {({ geographies }: any) =>
                            geographies.map((geo: any) => (
                                <Geography key={geo.rsmKey} fill="grey" geography={geo} />
                            ))
                        }
                    </Geographies>
                    {marks.map((mark, i) => (
                        <Marker key={i} coordinates={[mark.longitude, mark.latitude]}>
                            <circle r={3} fill="#FF5533" />
                        </Marker>
                    ))}
                </ZoomableGroup>
            </ComposableMap>
        </div>
    )
}
