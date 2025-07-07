'use client';

import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer,
  BarChart, Bar, AreaChart, Area, PieChart, Pie, Cell, Legend
} from 'recharts';

const attendanceData = [
  { month: 'Jan', present: 900 },
  { month: 'Feb', present: 1000 },
  { month: 'Mar', present: 950 },
  { month: 'Apr', present: 1100 },
  { month: 'May', present: 1130 },
  { month: 'Jun', present: 1075 },
];

const feeData = [
  { month: 'Jan', fees: 200000 },
  { month: 'Feb', fees: 240000 },
  { month: 'Mar', fees: 180000 },
  { month: 'Apr', fees: 290000 },
  { month: 'May', fees: 310000 },
  { month: 'Jun', fees: 275000 },
];

const studentGrowthData = [
  { month: 'Jan', students: 1200 },
  { month: 'Feb', students: 1225 },
  { month: 'Mar', students: 1250 },
  { month: 'Apr', students: 1300 },
  { month: 'May', students: 1350 },
  { month: 'Jun', students: 1400 },
];

const genderData = [
  { name: 'Male', value: 750 },
  { name: 'Female', value: 650 },
];

const COLORS = ['#60a5fa', '#f472b6']; // blue-400, pink-400
const total = genderData.reduce((sum, g) => sum + g.value, 0);

export default function AdminCharts() {
  return (
    <section className="grid grid-cols-1 xl:grid-cols-2 gap-6 mt-6">
      {/* Attendance Chart */}
      <div className="bg-white rounded-xl p-4 shadow-sm">
        <h2 className="font-semibold text-lg mb-2">📅 Attendance Trend</h2>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={attendanceData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="present" stroke="#7c3aed" strokeWidth={2} />
          </LineChart>
        </ResponsiveContainer>
      </div>

      {/* Fees Chart */}
      <div className="bg-white rounded-xl p-4 shadow-sm">
        <h2 className="font-semibold text-lg mb-2">💰 Monthly Fees Collected</h2>
        <ResponsiveContainer width="100%" height={250}>
          <BarChart data={feeData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="fees" fill="#10b981" radius={[6, 6, 0, 0]} />
          </BarChart>
        </ResponsiveContainer>
      </div>

      {/* Student Growth Chart */}
      <div className="bg-white rounded-xl p-4 shadow-sm">
        <h2 className="font-semibold text-lg mb-2">📈 Student Growth</h2>
        <ResponsiveContainer width="100%" height={250}>
          <AreaChart data={studentGrowthData}>
            <defs>
              <linearGradient id="colorStudents" x1="0" y1="0" x2="0" y2="1">
                <stop offset="5%" stopColor="#6366f1" stopOpacity={0.8} />
                <stop offset="95%" stopColor="#6366f1" stopOpacity={0} />
              </linearGradient>
            </defs>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Area type="monotone" dataKey="students" stroke="#6366f1" fillOpacity={1} fill="url(#colorStudents)" />
          </AreaChart>
        </ResponsiveContainer>
      </div>

      {/* Gender Donut Chart */}
      <div className="bg-white rounded-xl p-6 shadow-sm">
      <h2 className="font-semibold text-lg mb-4 text-center">👨‍👩‍👧‍👦 Gender Distribution</h2>
      <div className="flex flex-col lg:flex-row items-center justify-between gap-6">
        {/* Donut Chart */}
        <div className="w-full h-64">
          <ResponsiveContainer width="100%" height="100%">
            <PieChart>
              <Pie
                data={genderData}
                innerRadius={70}
                outerRadius={100}
                paddingAngle={3}
                dataKey="value"
              >
                {genderData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={COLORS[index]} />
                ))}
              </Pie>
              <Tooltip formatter={(value) => `${value} students`} />
            </PieChart>
          </ResponsiveContainer>
        </div>

        {/* Legend */}
        <div className="flex flex-col gap-2 text-sm">
          {genderData.map((entry, index) => {
            const percent = ((entry.value / total) * 100).toFixed(1);
            return (
              <div key={index} className="flex items-center gap-2">
                <span
                  className="inline-block w-3 h-3 rounded-full"
                  style={{ backgroundColor: COLORS[index] }}
                />
                <span className="text-gray-700 font-medium">{entry.name}:</span>
                <span className="text-gray-500">{entry.value} ({percent}%)</span>
              </div>
            );
          })}
        </div>
      </div>
    </div>
    </section>
  );
}
